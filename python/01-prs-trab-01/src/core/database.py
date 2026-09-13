import os
import json
from typing import List
from src.config import settings

METADATA_FILE = os.path.join(settings.storage.metadata_dir, "documents.json")


class MetadataSingletonManager:
    def __init__(self):
        self._data: List[dict] = self._initial_load()

    def _initial_load(self) -> List[dict]:
        if not os.path.exists(METADATA_FILE):
            with open(METADATA_FILE, "w", encoding="utf-8") as f:
                json.dump([], f)
            return []
        try:
            with open(METADATA_FILE, "r", encoding="utf-8") as f:
                return json.load(f)
        except json.JSONDecodeError:
            return []

    def get_all(self) -> List[dict]:
        return self._data

    def sync_and_save(self, new_data: List[dict]):
        self._data = new_data
        with open(METADATA_FILE, "w", encoding="utf-8") as f:
            json.dump(new_data, f, indent=2, ensure_ascii=False)

metadata_storage = MetadataSingletonManager()
