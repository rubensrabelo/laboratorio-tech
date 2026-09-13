import httpx
import os
import zlib
import struct
import json
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
    {"filename": "quantum_simulation_v1.py", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "description": "Auto inferred script file", "content": b"print('Simulation version 1.0')"},
    {"filename": "quantum_dataset.csv", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "description": "Auto inferred dataset csv file", "content": b"timestamp,metric\n1,0.95\n2,0.98"},
    {"filename": "quantum_final_report.pdf", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "description": "Auto inferred report pdf file", "content": generate_real_pdf("Relatorio Final - Computacao Quantica - Dr. Alan Turing")},
    
    {"filename": "dna_sequencing_data.csv", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "description": "Inferred data mapping structure", "content": b"gene_id,sequence\nAGTC01,ATCGGCTA"},
    {"filename": "crispr_analysis.py", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "description": "Inferred code analyzer block", "content": b"print('CRISPR analysis setup')"},
    {"filename": "gel_electrophoresis.png", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "description": "Inferred graphics simulation map", "content": generate_real_png()},
    {"filename": "genomics_draft.txt", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "description": "Inferred plain text document writeup", "content": b"Draft text for genomics publication..."},
    
    {"filename": "climate_temp_series.csv", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "description": "Inferred environment logging table", "content": b"year,anomaly\n2024,1.2\n2025,1.3"},
    {"filename": "satellite_map.png", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "description": "Inferred weather mapping capture", "content": generate_real_png()},
    {"filename": "predictive_model.py", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "description": "Inferred math python execution file", "content": b"def predict_temp(year): return year * 0.02"},
    {"filename": "annual_climate_review.pdf", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "description": "Inferred publication review file", "content": generate_real_pdf("Revisao Climatica Anual - Prof. Svante Arrhenius")},
    
    {"filename": "hubble_deep_field.png", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "description": "Inferred astronomical matrix array", "content": generate_real_png()},
    {"filename": "galaxy_redshift.csv", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "description": "Inferred tabular measurement chart", "content": b"galaxy,redshift\nNGC123,0.015"},
    {"filename": "spectrometry_script.py", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "description": "Inferred calculation engineering file", "content": b"print('Spectrometry parsing data')"},
    {"filename": "astrophysics_paper.pdf", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "description": "Inferred manuscript final submission file", "content": generate_real_pdf("Artigo Cientifico - Astrofisica - Dr. Edwin Hubble")}
]

def seed_database():
    print("Iniciando a carga de dados automatizada e geracao de binarios reais...")
    
    with httpx.Client() as client:
        for index, art in enumerate(artifacts, start=1):
            metadata_payload = {
                "project": art["project"],
                "researcher": art["researcher"],
                "description": art["description"]
            }
            
            form_data = {
                "metadata": json.dumps(metadata_payload)
            }
            files = {"file": (art["filename"], art["content"])}
            
            response = client.post(f"{BASE_URL}/documents", data=form_data, files=files)
            if response.status_code == 201:
                print(f"Sucesso [{index}/15]: Upload de '{art['filename']}' concluído com metadados enxutos via JSON.")
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

        print("\nTodos os testes foram injetados com sucesso pelas duas novas regras combinadas!")

if __name__ == "__main__":
    try:
        seed_database()
    except httpx.ConnectError:
        print(f"Erro: O servidor uvicorn não está rodando em {BASE_URL}. Inicie a API antes de rodar o script.")
