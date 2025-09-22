CREATE TABLE transaction (
  id BIGINT NOT NULL PRIMARY KEY,          -- Long for transaction
  fund_id BIGINT NOT NULL,                 -- FK → fund.id
  investor_id BIGINT NOT NULL,               -- FK → investor.id
  type VARCHAR(32) NOT NULL,                 -- CONTRIBUTION / DISTRIBUTION / etc.
  amount DECIMAL(15,2) NOT NULL,             -- transaction amount
  date DATE NOT NULL,                        -- transaction date

  CONSTRAINT transaction_fund FOREIGN KEY (fund_id) REFERENCES fund(id),
  CONSTRAINT transaction_investor FOREIGN KEY (investor_id) REFERENCES investor(id)
);