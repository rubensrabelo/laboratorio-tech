"""Hash configuration schema definition."""

from pydantic import BaseModel

class HashConfig(BaseModel):
    """Cryptographic signature algorithms specifications settings."""
    algorithm: str
