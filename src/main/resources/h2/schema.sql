create table if not exists customer
(
    customer_id     bigint       not null,
    name            varchar(255) not null,
    primary key (customer_id)
);

create table if not exists bank
(
    bank_id         bigint       not null,
    name            varchar(255) not null,
    primary key (bank_id)
);

create table if not exists bank_account
(
    bank_account_id         bigint       not null,
    customer_id             bigint       not null,
    bank_id                 bigint       not null,
    name                    varchar(255) not null,
    account_balance_pennies bigint       default 0,
    primary key (bank_account_id)
);
create index if not exists idx_cust_bank on bank_account (customer_id, bank_id);

create table if not exists customer_account
(
    customer_account_id     bigint       not null,
    customer_id             bigint       not null,
    account_name            varchar(255) not null,
    business_name           varchar(255) not null,
    primary key (customer_account_id)
);
create index if not exists idx_cust_account on customer_account (customer_id, account_name);

create table if not exists customer_bills
(
    customer_bill_id        bigint      not null,
    customer_account_id     bigint      not null,
    is_paid                 boolean default false,
    amount_due_pennies      bigint      not null,
    due_date                date        not null,
    primary key (customer_account_id)
);
create index if not exists idx_cust_bills on customer_bills (customer_account_id, is_paid);