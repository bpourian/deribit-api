-- CONSTANTS
CREATE TABLE IF NOT EXISTS public.currency_type
(
	type TEXT NOT NULL
		CONSTRAINT pk_currency_type PRIMARY KEY
);

INSERT INTO public.currency_type(type)
VALUES ('ETH'),
       ('BTC'),
       ('USDT');

CREATE TABLE IF NOT EXISTS public.account_type
(
	type TEXT NOT NULL
		CONSTRAINT pk_account_type PRIMARY KEY
);

INSERT INTO public.account_type(type)
VALUES ('MAIN'),
       ('SUBACCOUNT');

-- MAIN ENTRY LEVEL TABLE
CREATE TABLE IF NOT EXISTS public.account_holder
(
	account_id    INTEGER NOT NULL,
	type          TEXT    NOT NULL,
	email_address TEXT    NOT NULL,
	user_name     TEXT
);

ALTER TABLE public.account_holder ADD CONSTRAINT pk_account_holder PRIMARY KEY (account_id);
ALTER TABLE public.account_holder ADD CONSTRAINT fk_account_type_account_holder FOREIGN KEY (type) REFERENCES public.account_type(type);
CREATE INDEX idx_account_holder_email ON public.account_holder (email_address);

CREATE TABLE IF NOT EXISTS public.portfolio
(
	account_id      INTEGER NOT NULL,
	currency        TEXT    NOT NULL,
	current_balance NUMERIC,
	reserved_funds  NUMERIC
);
ALTER TABLE public.portfolio ADD CONSTRAINT pk_portfolio PRIMARY KEY (account_id, currency);
ALTER TABLE public.portfolio ADD CONSTRAINT fk_currency_account_holder FOREIGN KEY (account_id) REFERENCES public.account_holder(account_id);
ALTER TABLE public.portfolio ADD CONSTRAINT fk_currency_type_portfolio FOREIGN KEY (currency) REFERENCES public.currency_type(type);
