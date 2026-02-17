--Create table tb_user
CREATE TABLE IF NOT EXISTS tb_user (
                                       id UUID PRIMARY KEY,
                                       nom VARCHAR(255),
                                       prenom VARCHAR(255),
                                       role_enum VARCHAR(50),
                                       matricule VARCHAR(255) UNIQUE,
                                       date_naissance DATE,
                                       email VARCHAR(255),
                                       numero_telephone VARCHAR(50),
                                       statut_enum VARCHAR(50),
                                       username VARCHAR(255),
                                       password VARCHAR(255),
                                       agence_id UUID,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert test data
INSERT INTO tb_user (
    id, nom, prenom, role_enum, matricule, date_naissance,
    email, numero_telephone, statut_enum, username, password,
    agence_id, created_at, updated_at
) VALUES
      (RANDOM_UUID(), 'Doe', 'John', 'ADMINISTRATEUR', 'MAT-0001', DATE '1998-05-12', 'john@gmail.com', '70000001', 'ACTIVE', 'john.doe', 'password123', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (RANDOM_UUID(), 'Smith', 'Jane', 'OPERATEUR', 'MAT-0002', DATE '2000-11-03', 'jane@gmail.com', '70000002', 'ACTIVE', 'jane.smith', 'password456', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (RANDOM_UUID(), 'Johnson', 'Alice', 'CLIENT', 'MAT-0003', DATE '1997-01-25', 'alice@gmail.com', '70000003', 'DESACTIVE', 'alice.johnson', 'password789', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (RANDOM_UUID(), 'Boukary', 'Messi', 'AUDITEUR', 'MAT-0004', DATE '1999-01-25', 'messi@gmail.com', '70000004', 'ACTIVE', 'boukary.messi', 'password789', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (RANDOM_UUID(), 'Steve', 'Cenat', 'GESTIONNAIRE', 'MAT-0005', DATE '2006-04-15', 'cenat@gmail.com', '70000005', 'DESACTIVE', 'steve.cenat', 'password789', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      (RANDOM_UUID(), 'Cristiano', 'Ali', 'CLIENT', 'MAT-0006', DATE '1997-11-25', 'ali@gmail.com', '70000006', 'ACTIVE', 'cristiano.ali', 'password789', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
