import httpx
import os
import zlib
import struct
from io import BytesIO

BASE_URL = "http://127.0.0.1:8000"

def generate_real_pdf(text_content):
    pdf = BytesIO()
    pdf.write(b"%PDF-1.4\n")
    pdf.write(b"1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n")
    pdf.write(b"2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n")
    pdf.write(b"3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> >> >> /Contents 4 0 R >>\nendobj\n")
    stream_content = f"BT /F1 14 Tf 50 750 Td ({text_content}) Tj ET\n".encode('utf-8')
    pdf.write(b"4 0 obj\n<< /Length " + str(len(stream_content)).encode('utf-8') + b" >>\nstream\n")
    pdf.write(stream_content)
    pdf.write(b"\nendstream\nendobj\n")
    pdf.write(b"xref\n0 5\n0000000000 65535 f\n0000000009 00000 n\n0000000058 00000 n\n0000000110 00000 n\n0000000244 00000 n\ntrailer\n<< /Size 5 /Root 1 0 R >>\nstartxref\n320\n%%EOF")
    return pdf.getvalue()

def generate_real_png():
    width, height = 100, 100
    raw_data = bytearray()
    for y in range(height):
        raw_data.append(0)
        for x in range(width):
            r = int(x * 2.55)
            g = int(y * 2.55)
            b = 150
            raw_data.extend([r, g, b])
    
    def make_chunk(tag, data):
        return struct.pack(">I", len(data)) + tag + data + struct.pack(">I", zlib.crc32(tag + data))

    png = b"\x89PNG\r\n\x1a\n"
    ihdr_data = struct.pack(">IIBBBBB", width, height, 8, 2, 0, 0, 0)
    png += make_chunk(b"IHDR", ihdr_data)
    png += make_chunk(b"IDAT", zlib.compress(raw_data))
    png += make_chunk(b"IEND", b"")
    return png

artifacts = [
    {"filename": "quantum_simulation_v1.py", "category": "scripts", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-01-15", "content": b"print('Simulation version 1.0')"},
    {"filename": "quantum_dataset.csv", "category": "data", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "artifact_type": "Dataset", "research_stage": "Data Collection", "reference_date": "2026-02-10", "content": b"timestamp,metric\n1,0.95\n2,0.98"},
    {"filename": "quantum_final_report.pdf", "category": "reports", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "artifact_type": "Report", "research_stage": "Writing", "reference_date": "2026-03-01", "content": generate_real_pdf("Relatorio Final - Computacao Quantica - Dr. Alan Turing")},
    
    {"filename": "dna_sequencing_data.csv", "category": "data", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Dataset", "research_stage": "Data Collection", "reference_date": "2026-04-12", "content": b"gene_id,sequence\nAGTC01,ATCGGCTA"},
    {"filename": "crispr_analysis.py", "category": "scripts", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-05-20", "content": b"print('CRISPR analysis setup')"},
    {"filename": "gel_electrophoresis.png", "category": "images", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Image", "research_stage": "Review", "reference_date": "2026-05-25", "content": generate_real_png()},
    {"filename": "genomics_draft.txt", "category": "reports", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Report", "research_stage": "Writing", "reference_date": "2026-06-02", "content": b"Draft text for genomics publication..."},
    
    {"filename": "climate_temp_series.csv", "category": "data", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Dataset", "research_stage": "Data Collection", "reference_date": "2026-01-20", "content": b"year,anomaly\n2024,1.2\n2025,1.3"},
    {"filename": "satellite_map.png", "category": "images", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Image", "research_stage": "Data Collection", "reference_date": "2026-02-15", "content": generate_real_png()},
    {"filename": "predictive_model.py", "category": "scripts", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-03-10", "content": b"def predict_temp(year): return year * 0.02"},
    {"filename": "annual_climate_review.pdf", "category": "reports", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Report", "research_stage": "Review", "reference_date": "2026-04-05", "content": generate_real_pdf("Revisao Climatica Anual - Prof. Svante Arrhenius")},
    
    {"filename": "hubble_deep_field.png", "category": "images", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Image", "research_stage": "Data Collection", "reference_date": "2026-07-19", "content": generate_real_png()},
    {"filename": "galaxy_redshift.csv", "category": "data", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Dataset", "research_stage": "Analysis", "reference_date": "2026-08-01", "content": b"galaxy,redshift\nNGC123,0.015"},
    {"filename": "spectrometry_script.py", "category": "scripts", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-08-22", "content": b"print('Spectrometry parsing data')"},
    {"filename": "astrophysics_paper.pdf", "category": "reports", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Report", "research_stage": "Writing", "reference_date": "2026-09-05", "content": generate_real_pdf("Artigo Cientifico - Astrofisica - Dr. Edwin Hubble")}
]

def seed_database():
    print("Iniciando a carga de dados automatizada e geracao de binarios reais...")
    
    with httpx.Client() as client:
        for index, art in enumerate(artifacts, start=1):
            form_data = {
                "category": art["category"],
                "description": f"Arquivo de teste número {index} correspondente ao artefato {art['filename']}",
                "project": art["project"],
                "researcher": art["researcher"],
                "artifact_type": art["artifact_type"],
                "research_stage": art["research_stage"],
                "reference_date": art["reference_date"]
            }
            files = {"file": (art["filename"], art["content"])}
            
            response = client.post(f"{BASE_URL}/documents", data=form_data, files=files)
            if response.status_code == 201:
                print(f"Sucesso [{index}/15]: Upload de '{art['filename']}' concluído com binário real.")
            else:
                print(f"Erro no upload de '{art['filename']}': {response.text}")
                return

        print("\nTestando listagem com filtros (Projeto: Quantum Computing)...")
        filter_res = client.get(f"{BASE_URL}/documents?project=Quantum Computing")
        print(f"Total encontrado no projeto: {len(filter_res.json())} arquivos.")

        print("\nBuscando estatísticas gerais do painel...")
        stats_res = client.get(f"{BASE_URL}/operations/stats")
        print("Métricas retornadas:", stats_res.json())

        print("\nExecutando verificação global de integridade...")
        integrity_res = client.get(f"{BASE_URL}/operations/integrity-check")
        print("Resultado da integridade:", integrity_res.json())

        print("\nGerando backup seletivo para o projeto 'Genomics Research'...")
        backup_res = client.post(f"{BASE_URL}/operations/backup/project?project=Genomics Research")
        print("Resultado do backup seletivo:", backup_res.json())

        print("\nTodos os testes foram injetados")

if __name__ == "__main__":
    try:
        seed_database()
    except httpx.ConnectError:
        print(f"Erro: O servidor uvicorn não está rodando em {BASE_URL}. Inicie a API antes de rodar o script.")
