CREATE TABLE IF NOT EXISTS account (
    id bigint PRIMARY KEY NOT NULL,
    document CHARACTER VARYING(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id bigint PRIMARY KEY NOT NULL,
    account_id bigint NOT NULL,
    operation_type CHARACTER VARYING(20) NOT NULL,
    amount numeric(15,2) NOT NULL,
    event_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE SEQUENCE seq_account;
