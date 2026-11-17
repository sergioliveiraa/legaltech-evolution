INSERT INTO localidade (cidade, uf, regiao, latitude, longitude) VALUES
                                                                     ('São Paulo', 'SP', 'Sudeste', -23.5505, -46.6333),
                                                                     ('Rio de Janeiro', 'RJ', 'Sudeste', -22.9068, -43.1729);

INSERT INTO legaltech (nome_legaltech, segmento, porte, ano_fundacao, modelo_negocio, investimento_estimado, localidade_id) VALUES
                                                                                                                                ('JusAnalytics Tech', 'Analytics Jurídico', 'Médio', 2020, 'SaaS', 1500000.00, 1),
                                                                                                                                ('DocSmart Legal', 'Gestão Documental', 'Pequeno', 2022, 'Assinatura', 500000.00, 2);

INSERT INTO cliente (tipo_cliente, porte_cliente, area_juridica_principal, canal_aquisicao, data_cadastro, localidade_id) VALUES
                                                                                                                              ('Pessoa Jurídica', 'Grande Porte', 'Contratos', 'Inbound Marketing', '2024-01-15', 1),
                                                                                                                              ('Escritório', 'Médio Porte', 'Trabalhista', 'Indicação Direta', '2024-02-01', 2);

INSERT INTO vinculo (cliente_id, legaltech_id, data_inicio, data_fim, status_vinculo) VALUES
                                                                                          (1, 1, '2024-01-16', NULL, 'ATIVO'),
                                                                                          (2, 2, '2024-02-02', NULL, 'ATIVO');

INSERT INTO plano (nome_plano, valor_referencia) VALUES
                                                     ('Enterprise Standard', 2500.00),
                                                     ('Professional Pro', 1200.00);

INSERT INTO contrato (vinculo_id, plano_id, data_inicio, data_fim, valor_mensal, data_cancelamento) VALUES
                                                                                                        (1, 1, '2024-01-16', NULL, 2400.00, NULL),
                                                                                                        (2, 2, '2024-02-02', NULL, 1200.00, NULL);

INSERT INTO historico_status_contrato (contrato_id, status_contrato, data_mudanca) VALUES
                                                                                       (1, 'ATIVO', '2024-01-16 09:00:00'),
                                                                                       (2, 'ATIVO', '2024-02-02 10:30:00');