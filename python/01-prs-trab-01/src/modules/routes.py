"""Centralized application routing matrix unifying all module endpoints."""

from fastapi import APIRouter
from src.modules.vault.routes import router as vault_router
from src.modules.operations.routes import router as operations_router

api_router = APIRouter()

api_router.include_router(vault_router)
api_router.include_router(operations_router)
