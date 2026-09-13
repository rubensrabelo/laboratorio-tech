"""Vault module services for handling physical secure file persistence operations."""

import os
import aiofiles
from src.config.settings import settings


async def save_secure_file(file_bytes: bytes, stored_name: str) -> str:
    """Save raw file bytes into the configured local storage directory asynchronously."""
    file_path = os.path.join(settings.storage.documents_dir, stored_name)
    async with aiofiles.open(file_path, "wb") as f:
        await f.write(file_bytes)
    return file_path
