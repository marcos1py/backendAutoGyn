import random
from datetime import datetime, timedelta
import psycopg
from faker import Faker

fake = Faker('pt_BR')

# Configuração do banco
DB_CONFIG = {
    'host': 'localhost',
    'port': 5432,
    'dbname': 'autogynDb',     # psycopg3 usa 'dbname' em vez de 'database'
    'user': 'postgres',     # Substitua pelo seu usuário
    'password': 'postgres'    # Substitua pela sua senha
}

def conectar_db():
    return psycopg.connect(**DB_CONFIG)

def gerar_funcionarios(conn, quantidade=20):
    cursor = conn.cursor()
    
    funcionarios = []
    for i in range(quantidade):
        nome = fake.name()
        cpf = fake.cpf()
        email = fake.email()
        telefone = fake.phone_number()
        endereco = fake.address()
        data_nascimento = fake.date_of_birth(minimum_age=18, maximum_age=65)
        data_admissao = fake.date_between(start_date='-5y', end_date='today')
        cargo = random.choice(['Mecânico', 'Eletricista', 'Pintor', 'Funileiro', 'Supervisor'])
        salario = f"{random.randint(2000, 8000):.2f}"
        situacao = 'Ativo'
        rg = f"{random.randint(10000000, 99999999)}"
        
        cursor.execute("""
            INSERT INTO funcionario (nome, cpf, email, telefone, endereco, data_nascimento, 
                                   data_admissao, cargo, salario, situacao, rg)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            RETURNING id
        """, (nome, cpf, email, telefone, endereco, data_nascimento, data_admissao, 
              cargo, salario, situacao, rg))
        
        funcionario_id = cursor.fetchone()[0]
        funcionarios.append(funcionario_id)
    
    conn.commit()
    return funcionarios

def gerar_clientes(conn, quantidade=200):
    cursor = conn.cursor()
    
    clientes = []
    for i in range(quantidade):
        nome = fake.name()
        cpf = fake.cpf()
        email = fake.email()
        logradouro = fake.street_address()
        numero = str(random.randint(1, 9999))
        complemento = random.choice(['Apt 101', 'Casa', 'Bloco A', None])
        ddd1 = random.randint(11, 99)
        numero1 = random.randint(900000000, 999999999)
        tipo_cliente = 'Pessoa Física'
        
        cursor.execute("""
            INSERT INTO cliente (nome, cpf, email, logradouro, numero, complemento, 
                               ddd1, numero1, tipo_cliente)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
            RETURNING id
        """, (nome, cpf, email, logradouro, numero, complemento, ddd1, numero1, tipo_cliente))
        
        cliente_id = cursor.fetchone()[0]
        clientes.append(cliente_id)
    
    conn.commit()
    return clientes

def gerar_marcas_modelos(conn):
    cursor = conn.cursor()
    
    marcas_modelos = {
        'Toyota': ['Corolla', 'Hilux', 'Camry', 'RAV4', 'Prius'],
        'Honda': ['Civic', 'Accord', 'CR-V', 'Fit', 'HR-V'],
        'Ford': ['Fiesta', 'Focus', 'Fusion', 'EcoSport', 'Ranger'],
        'Chevrolet': ['Onix', 'Prisma', 'Cruze', 'S10', 'Tracker'],
        'Volkswagen': ['Gol', 'Polo', 'Jetta', 'Tiguan', 'Amarok'],
        'Fiat': ['Uno', 'Palio', 'Punto', 'Toro', 'Mobi'],
        'Hyundai': ['HB20', 'Elantra', 'Tucson', 'Santa Fe', 'Creta'],
        'Nissan': ['March', 'Versa', 'Sentra', 'X-Trail', 'Frontier']
    }
    
    marca_ids = {}
    for marca_nome in marcas_modelos.keys():
        cursor.execute("INSERT INTO marca (nome) VALUES (%s) RETURNING id", (marca_nome,))
        marca_id = cursor.fetchone()[0]
        marca_ids[marca_nome] = marca_id
    
    modelo_ids = []
    for marca_nome, modelos in marcas_modelos.items():
        marca_id = marca_ids[marca_nome]
        for modelo_nome in modelos:
            cursor.execute("INSERT INTO modelo (nome, marca) VALUES (%s, %s) RETURNING id", 
                         (modelo_nome, marca_id))
            modelo_id = cursor.fetchone()[0]
            modelo_ids.append(modelo_id)
    
    conn.commit()
    return modelo_ids

def gerar_veiculos(conn, modelo_ids, quantidade=300):
    cursor = conn.cursor()
    
    placas = []
    for i in range(quantidade):
        placa = f"{fake.license_plate()}"
        chassi = f"{random.randint(10000000000000000, 99999999999999999)}"
        ano_fabricacao = random.randint(2010, 2024)
        ano_modelo = ano_fabricacao + random.randint(0, 1)
        quilometragem = random.randint(0, 200000)
        id_modelo = random.choice(modelo_ids)
        patrimonio = f"PAT{random.randint(1000, 9999)}"
        
        cursor.execute("""
            INSERT INTO veiculo (placa, chassi, ano_fabricacao, ano_modelo, 
                               quilometragem, id_modelo, patrimonio)
            VALUES (%s, %s, %s, %s, %s, %s, %s)
        """, (placa, chassi, ano_fabricacao, ano_modelo, quilometragem, id_modelo, patrimonio))
        
        placas.append(placa)
    
    conn.commit()
    return placas

def gerar_servicos(conn):
    cursor = conn.cursor()
    
    servicos = [
        ('Troca de Óleo', 80.00),
        ('Alinhamento', 120.00),
        ('Balanceamento', 100.00),
        ('Revisão Geral', 350.00),
        ('Troca de Pastilha de Freio', 180.00),
        ('Troca de Filtros', 150.00),
        ('Diagnóstico Eletrônico', 200.00),
        ('Troca de Correia Dentada', 450.00),
        ('Reparo de Motor', 1200.00),
        ('Pintura', 800.00),
        ('Funilaria', 600.00),
        ('Instalação de Som', 250.00),
        ('Troca de Pneus', 400.00),
        ('Limpeza de Bicos', 160.00),
        ('Teste de Bateria', 50.00)
    ]
    
    servico_ids = []
    for nome, preco in servicos:
        cursor.execute("INSERT INTO servico (nome, preco_unitario) VALUES (%s, %s) RETURNING id", 
                      (nome, preco))
        servico_id = cursor.fetchone()[0]
        servico_ids.append(servico_id)
    
    conn.commit()
    return servico_ids

def gerar_data_aleatoria_2025():
    start_date = datetime(2025, 1, 1)
    end_date = datetime(2025, 12, 31)
    
    time_between = end_date - start_date
    days_between = time_between.days
    random_days = random.randrange(days_between)
    
    return start_date + timedelta(days=random_days)

def gerar_ordens_servico(conn, cliente_ids, placa_veiculos, quantidade=1000):
    cursor = conn.cursor()
    
    ordem_ids = []
    for i in range(quantidade):
        data = gerar_data_aleatoria_2025().date()
        id_cliente = random.choice(cliente_ids)
        placa_veiculo = random.choice(placa_veiculos)
        preco_final = random.uniform(100.0, 2000.0)
        status = random.choice(['Aberta', 'Em Andamento', 'Concluída', 'Cancelada'])
        
        cursor.execute("""
            INSERT INTO ordem_servico (data, id_cliente, placa_veiculo, preco_final, status)
            VALUES (%s, %s, %s, %s, %s)
            RETURNING numero
        """, (data, id_cliente, placa_veiculo, preco_final, status))
        
        ordem_numero = cursor.fetchone()[0]
        ordem_ids.append(ordem_numero)
    
    conn.commit()
    return ordem_ids

def gerar_itens_servico(conn, ordem_ids, funcionario_ids, servico_ids):
    cursor = conn.cursor()
    
    for ordem_id in ordem_ids:
        # Cada ordem terá entre 1 e 4 serviços
        num_servicos = random.randint(1, 4)
        
        for _ in range(num_servicos):
            funcionario = random.choice(funcionario_ids)
            id_servico = random.choice(servico_ids)
            numero_os = ordem_id
            quantidade = random.randint(1, 3)
            preco_total = random.uniform(50.0, 800.0)
            
            # Gerar horários aleatórios
            data_base = gerar_data_aleatoria_2025()
            horario_inicio = data_base.replace(
                hour=random.randint(8, 16),
                minute=random.randint(0, 59)
            )
            horario_fim = horario_inicio + timedelta(
                hours=random.randint(1, 4),
                minutes=random.randint(0, 59)
            )
            
            cursor.execute("""
                INSERT INTO itens_servico (funcionario, id_servico, numero_os, quantidade, 
                                         preco_total, horario_inicio, horario_fim)
                VALUES (%s, %s, %s, %s, %s, %s, %s)
            """, (funcionario, id_servico, numero_os, quantidade, preco_total, 
                  horario_inicio, horario_fim))
    
    conn.commit()

def main():
    print("🔧 Iniciando geração de dados para Sistema de Oficina...")
    
    try:
        conn = conectar_db()
        print("✅ Conectado ao banco de dados")
        
        print("👥 Gerando funcionários...")
        funcionarios = gerar_funcionarios(conn, 20)
        print(f"✅ {len(funcionarios)} funcionários criados")
        
        print("🏢 Gerando clientes...")
        clientes = gerar_clientes(conn, 200)
        print(f"✅ {len(clientes)} clientes criados")
        
        print("🚗 Gerando marcas e modelos...")
        modelos = gerar_marcas_modelos(conn)
        print(f"✅ {len(modelos)} modelos criados")
        
        print("🚙 Gerando veículos...")
        veiculos = gerar_veiculos(conn, modelos, 300)
        print(f"✅ {len(veiculos)} veículos criados")
        
        print("🔧 Gerando serviços...")
        servicos = gerar_servicos(conn)
        print(f"✅ {len(servicos)} serviços criados")
        
        print("📋 Gerando ordens de serviço...")
        ordens = gerar_ordens_servico(conn, clientes, veiculos, 1000)
        print(f"✅ {len(ordens)} ordens de serviço criadas")
        
        print("⚙️ Gerando itens de serviço...")
        gerar_itens_servico(conn, ordens, funcionarios, servicos)
        print("✅ Itens de serviço criados")
        
        conn.close()
        print("🎉 Dados gerados com sucesso!")
        print(f"📊 Resumo:")
        print(f"   - 20 funcionários")
        print(f"   - 200 clientes")
        print(f"   - 300 veículos")
        print(f"   - 15 tipos de serviços")
        print(f"   - 1000 ordens de serviço em 2025")
        print(f"   - ~2500 itens de serviço")
        
    except Exception as e:
        print(f"❌ Erro: {e}")

if __name__ == "__main__":
    main()
