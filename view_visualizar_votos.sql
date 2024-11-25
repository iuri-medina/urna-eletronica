

CREATE OR REPLACE VIEW view_candidatos_votos AS
SELECT 
    c.numero AS numero,
    c.nome AS nome_candidato,
    p.nome AS nome_partido,
    COUNT(v.id) AS votos
FROM candidato c
LEFT JOIN voto v ON c.numero = v.candidato_numero
JOIN partido p ON c.partido_id = p.id
GROUP BY c.id, c.nome, p.nome
ORDER BY votos DESC;

DROP VIEW view_candidatos_votos