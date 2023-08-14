DELIMITER //
CREATE PROCEDURE GetCryptoAmount(IN username VARCHAR(255), IN cryptoSymbol VARCHAR(255))
BEGIN
    SET @columns = NULL;
    SELECT GROUP_CONCAT(CONCAT('`', COLUMN_NAME, '`')) INTO @columns 
    FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = 'pinance_database' 
    AND TABLE_NAME = 'account_balance' 
    AND COLUMN_NAME LIKE CONCAT('%', cryptoSymbol, '%');
    
    SET @query = CONCAT('SELECT ', @columns, ' FROM account_balance ab WHERE ab.username = ', QUOTE(username));
    PREPARE stmt FROM @query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE UpdateCryptoAmount(IN username VARCHAR(255), IN cryptoSymbol VARCHAR(255), IN amount DECIMAL(18, 8))
BEGIN
    SET @columnToUpdate = NULL;
    SELECT COLUMN_NAME INTO @columnToUpdate
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = 'pinance_database'
    AND TABLE_NAME = 'account_balance'
    AND COLUMN_NAME LIKE CONCAT('%', cryptoSymbol, '%');

    IF @columnToUpdate IS NOT NULL THEN
        SET @query = CONCAT('UPDATE account_balance SET ', @columnToUpdate, ' = ', amount, ' WHERE username = ', QUOTE(username));
        PREPARE stmt FROM @query;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END //
DELIMITER ;
