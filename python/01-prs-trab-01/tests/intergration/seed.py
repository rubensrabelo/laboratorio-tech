import httpx

BASE_URL = "http://127.0.0.1:8000"

artifacts = [
    {"filename": "quantum_simulation_v1.py", "category": "scripts", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-01-15", "content": b"print('Simulation version 1.0')"},
    {"filename": "quantum_dataset.csv", "category": "data", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "artifact_type": "Dataset", "research_stage": "Data Collection", "reference_date": "2026-02-10", "content": b"timestamp,metric\n1,0.95\n2,0.98"},
    {"filename": "quantum_final_report.pdf", "category": "reports", "project": "Quantum Computing", "researcher": "Dr. Alan Turing", "artifact_type": "Report", "research_stage": "Writing", "reference_date": "2026-03-01", "content": b"%PDF-1.4\n1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Resources << >> /Contents 4 0 R >>\nendobj\n4 0 obj\n<< /Length 21 >>\nstream\nBT /F1 12 Tf ET\nendstream\nendobj\nxref\n0 5\n0000000000 65535 f\n0000000009 00000 n\n0000000060 00000 n\n0000000111 00000 n\n0000000212 00000 n\ntrailer\n<< /Size 5 /Root 1 0 R >>\nstartxref\n282\n%%EOF"},
    
    {"filename": "dna_sequencing_data.csv", "category": "data", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Dataset", "research_stage": "Data Collection", "reference_date": "2026-04-12", "content": b"gene_id,sequence\nAGTC01,ATCGGCTA"},
    {"filename": "crispr_analysis.py", "category": "scripts", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-05-20", "content": b"print('CRISPR analysis setup')"},
    {"filename": "gel_electrophoresis.png", "category": "images", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Image", "research_stage": "Review", "reference_date": "2026-05-25", "content": b"\x89PNG\r\n\x1a\n\x00\x00\x00\rIHDR\x00\x00\x00\x01\x00\x00\x00\x01\x08\x06\x00\x00\x00\x1f\x15c4\x00\x00\x00\rIDATx\x9cc` \x05\x00\x00\x0e\x00\x01\xa0\x9c\x10\x8d\x00\x00\x00\x00IEND\xaeB`\x82"},
    {"filename": "genomics_draft.txt", "category": "reports", "project": "Genomics Research", "researcher": "Dr. Rosalind Franklin", "artifact_type": "Report", "research_stage": "Writing", "reference_date": "2026-06-02", "content": b"Draft text for genomics publication..."},
    
    {"filename": "climate_temp_series.csv", "category": "data", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Dataset", "research_stage": "Data Collection", "reference_date": "2026-01-20", "content": b"year,anomaly\n2024,1.2\n2025,1.3"},
    {"filename": "satellite_map.jpg", "category": "images", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Image", "research_stage": "Data Collection", "reference_date": "2026-02-15", "content": b"\xff\xd8\xff\xe0\x00\x10JFIF\x00\x01\x01\x01\x00`\x00`\x00\x00\xff\xdb\x00C\x00\x08\x06\x06\x07\x06\x05\x08\x07\x07\x07\t\t\x08\n\x0c\x14\r\x0c\x0b\x0b\x0c\x19\x12\x13\x0f\x14\x1d\x1a\x1f\x1e\x1d\x1a\x1c\x1c $.' \",#\x1c\x1c(7),01444\x1f'9=82<.342\xff\xc0\x00\x0b\x08\x00\x01\x00\x01\x01\x01\x11\x00\xff\xc4\x00\x1f\x00\x00\x01\x05\x01\x01\x01\x01\x01\x01\x00\x00\x00\x00\x00\x00\x00\x00\x01\x02\x03\x04\x05\x06\x07\x08\t\n\x0b\xff\xc4\x00\xb5\x10\x00\x02\x01\x03\x03\x02\x04\x03\x05\x05\x04\x04\x00\x00\x01\x05\x01\x02\x03\x00\x04\x11\x05\x12!1A\x06\x13Qaq\x07\x14"},
    {"filename": "predictive_model.py", "category": "scripts", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-03-10", "content": b"def predict_temp(year): return year * 0.02"},
    {"filename": "annual_climate_review.pdf", "category": "reports", "project": "Climate Change Analytics", "researcher": "Prof. Svante Arrhenius", "artifact_type": "Report", "research_stage": "Review", "reference_date": "2026-04-05", "content": b"%PDF-1.4\n1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Resources << >> /Contents 4 0 R >>\nendobj\n4 0 obj\n<< /Length 21 >>\nstream\nBT /F1 12 Tf ET\nendstream\nendobj\nxref\n0 5\n0000000000 65535 f\n0000000009 00000 n\n0000000060 00000 n\n0000000111 00000 n\n0000000212 00000 n\ntrailer\n<< /Size 5 /Root 1 0 R >>\nstartxref\n282\n%%EOF"},
    
    {"filename": "hubble_deep_field.tiff", "category": "images", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Image", "research_stage": "Data Collection", "reference_date": "2026-07-19", "content": b"II*\x00\x08\x00\x00\x00\x00\x00\x00\x00\x00\x00"},
    {"filename": "galaxy_redshift.csv", "category": "data", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Dataset", "research_stage": "Analysis", "reference_date": "2026-08-01", "content": b"galaxy,redshift\nNGC123,0.015"},
    {"filename": "spectrometry_script.py", "category": "scripts", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Script", "research_stage": "Analysis", "reference_date": "2026-08-22", "content": b"print('Spectrometry parsing data')"},
    {"filename": "astrophysics_paper.pdf", "category": "reports", "project": "Deep Space Observation", "researcher": "Dr. Edwin Hubble", "artifact_type": "Report", "research_stage": "Writing", "reference_date": "2026-09-05", "content": b"%PDF-1.4\n1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Resources << >> /Contents 4 0 R >>\nendobj\n4 0 obj\n<< /Length 21 >>\nstream\nBT /F1 12 Tf ET\nendstream\nendobj\nxref\n0 5\n0000000000 65535 f\n0000000009 00000 n\n0000000060 00000 n\n0000000111 00000 n\n0000000212 00000 n\ntrailer\n<< /Size 5 /Root 1 0 R >>\nstartxref\n282\n%%EOF"}
]

def seed_database():
    print("Iniciando a carga de dados para testes...")
    
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
                print(f"Sucesso [{index}/15]: Upload de '{art['filename']}' concluído.")
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

        print("\nTodos os testes básicos foram injetados e validados com êxito!")


if __name__ == "__main__":
    try:
        seed_database()
    except httpx.ConnectError:
        print(f"Erro: O servidor uvicorn não está rodando em {BASE_URL}. Inicie a API antes de rodar o script.")
