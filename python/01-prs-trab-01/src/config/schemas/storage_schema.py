"""Storage configuration schema definition."""

from pydantic import BaseModel

class StorageConfig(BaseModel):
    """Storage directory parameters metadata representation."""
    documents_dir: str
    metadata_dir: str
    backups_dir: str
    logs_dir: str
