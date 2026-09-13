"""Systemic tracking interface adapter wrapping standard Python logging modules."""

import logging
from src.config import settings

def setup_logging():
    """Initialize system-wide environmental logging interface setups using target 
    structural layouts mapping configurations."""
    logging.basicConfig(
        filename=settings.logging.file_path,
        level=getattr(logging, settings.logging.level.upper(), logging.INFO),
        format="%(asctime)s %(levelname)s %(message)s",
        datefmt="%Y-%m-%d %H:%M:%S"
    )

def log_event(level: str, operation: str, details: str):
    """Write standard systemic application audit log reports metrics targeting
    runtime operational tracking pipelines."""
    logger = logging.getLogger("science_vault")
    message = f"{operation} {details}"

    if level.upper() == "INFO":
        logger.info(message)
    elif level.upper() == "WARNING":
        logger.warning(message)
    elif level.upper() == "ERROR":
        logger.error(message)
