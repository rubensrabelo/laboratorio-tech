-- Atualiza os registros finalizados (garante integridade)
UPDATE order_items 
SET preparation_started_at = NOW(), ready_at = NOW(), delivered_at = NOW() 
WHERE status = 'DELIVERED';

UPDATE order_items 
SET preparation_started_at = NOW(), ready_at = NOW() 
WHERE status = 'READY';

-- ALTERADO PARA O TESTE: Joga o início do preparo para 1 HORA ATRÁS
-- Isso vai forçar o item a ficar atrasado em relação ao tempo do produto
UPDATE order_items 
SET preparation_started_at = NOW() - INTERVAL '1 HOUR' 
WHERE status = 'PREPARING';
