-- Exemplo de atualização de registros antigos para não ficarem nulos (opcional)
UPDATE order_items 
SET preparation_started_at = NOW(), ready_at = NOW(), delivered_at = NOW() 
WHERE status = 'DELIVERED';

UPDATE order_items 
SET preparation_started_at = NOW(), ready_at = NOW() 
WHERE status = 'READY';

UPDATE order_items 
SET preparation_started_at = NOW() 
WHERE status = 'PREPARING';
