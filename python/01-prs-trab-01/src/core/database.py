"""Centralized core database singleton manager for system metadata."""

import os
import json
from typing import List
from types import SimpleNamespace
from src.config import settings

METADATA_FILE = os.path.join(settings.storage.metadata_dir, "documents.json")

def _initial_load() -> List[dict]:
    """Perform initial configuration state loading from flat persistent JSON file storage layer."""
    if not os.path.exists(METADATA_FILE):
        with open(METADATA_FILE, "w", encoding="utf-8") as f:
            json.dump([], f)
        return []
    try:
        with open(METADATA_FILE, "r", encoding="utf-8") as f:
            return json.load(f)
    except json.JSONDecodeError:
        return []

_data_store: List[List[dict]] = [_initial_load()]

def _get_all() -> List[dict]:
    """Retrieve unified memory catalog block records collections matrix entries."""
    return _data_store[0]

def _sync_and_save(new_data: List[dict]):
    """Synchronize incoming collection arrays dataset updates directly to underlying disk files systems."""
    _data_store[0] = new_data
    with open(METADATA_FILE, "w", encoding="utf-8") as f:
        json.dump(new_data, f, indent=2, ensure_ascii=False)


metadata_storage = SimpleNamespace(
    get_all=_get_all,
    sync_and_save=_sync_and_save
)
