"""Backup configuration schema definition."""

from pydantic import BaseModel

class BackupConfig(BaseModel):
    """Archival compression storage standard format type specification."""
    format: str
