"""Logging configuration schema definition."""

from pydantic import BaseModel

class LoggingConfig(BaseModel):
    """Operational records file target outputs specifications."""
    file_path: str
    level: str
