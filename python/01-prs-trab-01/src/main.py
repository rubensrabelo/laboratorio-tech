"""Application entrypoint server initializations configurations layer."""

from fastapi import FastAPI
from src.core import setup_logging, log_event
from src.modules import api_router

app = FastAPI(
    title="Science Research Artifacts Vault API",
    description="Modular monolith digital secure repository.",
    version="1.0.0"
)

setup_logging()
log_event("INFO", "SYSTEM_START", "Application ecosystem initialized.")

app.include_router(api_router)


@app.get("/")
def root():
    """Root server context mapping diagnostic checks profiles entries."""
    return {
        "status": "online",
        "system": "Science Research Artifacts Vault",
        "documentation_url": "/docs"
    }
