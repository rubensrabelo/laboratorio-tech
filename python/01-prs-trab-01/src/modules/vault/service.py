import os
import aiofiles
from src.config.settings import settings


async def save_secure_file(file_bytes: bytes, stored_name: str) -> str:
    file_path = os.path.join(settings.storage.documents_dir, stored_name)
    async with aiofiles.open(file_path, "wb") as f:
        await f.write(file_bytes)
    return file_path
