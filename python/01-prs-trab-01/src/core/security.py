"""Cryptographic security wrappers handling hashing algorithms calculations wrappers."""

import hashlib

def calculate_sha256(file_content: bytes) -> str:
    """Compute structural sha256 checksum signatures directly over primitive streaming
    raw file byte array targets."""
    sha256_hash = hashlib.sha256()
    sha256_hash.update(file_content)
    return sha256_hash.hexdigest()
