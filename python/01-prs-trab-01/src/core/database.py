"""Centralized core database singleton manager for system metadata."""

import os
import json
from typing import List
from src.config import settings

METADATA_FILE = os.path.join(settings.storage.metadata_dir, "documents.json")

class MetadataSingletonManager:
    """Core memory database adapter managing single ledger replication states."""

    def __init__(self):
        self._data: List[dict] = self._initial_load()

    def _initial_load(self) -> List[dict]:
        """
        Perform initial configuration state loading from flat persistent JSON file storage layer.
        """
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
        """Retrieve unified memory catalog block records collections matrix entries."""
        return self._data

    def sync_and_save(self, new_data: List[dict]):
        """
        Synchronize incoming collection arrays dataset updates directly to underlying disk 
        files systems.
        """
        self._data = new_data
        with open(METADATA_FILE, "w", encoding="utf-8") as f:
            json.dump(new_data, f, indent=2, ensure_ascii=False)


metadata_storage = MetadataSingletonManager()
