"""Upload configuration schema definition."""

from pydantic import BaseModel

class UploadConfig(BaseModel):
    """File upload size limits specification details."""
    max_size_mb: int
