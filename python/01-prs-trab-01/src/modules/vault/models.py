"""Vault module data models representing scientific research artifact attributes schemas."""

from typing import Optional
from pydantic import BaseModel


class DocumentMetadata(BaseModel):
    """Unified application settings metadata representation layout."""

    id: int
    original_name: str
    stored_name: str
    extension: str
    mime_type: str
    size: int
    category: str
    description: str
    upload_date: str
    sha256: str
    project: str
    researcher: str
    artifact_type: str
    research_stage: str
    reference_date: str


class DocumentUpdate(BaseModel):
    """Data transfer schema representing editable document properties validation rules."""

    category: Optional[str] = None
    description: Optional[str] = None
    project: Optional[str] = None
    researcher: Optional[str] = None
    artifact_type: Optional[str] = None
    research_stage: Optional[str] = None
