create table if not exists customers
(
    customer_id     bigint       not null,
    name            varchar(255) not null,
    primary key (customer_id)
);

create table if not exists banks
(
    bank_id         bigint       not null,
    name            varchar(255) not null,
    primary key (bank_id)
);

create table if not exists bank_accounts
(
    bank_account_id         bigint       not null,
    customer_id             bigint       not null,
    bank_id                 bigint       not null,
    name                    varchar(255) not null,
    routing_number          varchar(32)  not null,
    account_number          varchar(32)  not null,
    primary key (bank_account_id)
);
create index if not exists idx_cust_bank on bank_accounts (customer_id, bank_id);

create table if not exists customer_accounts
(
    customer_account_id     bigint       not null,
    customer_id             bigint       not null,
    account_name            varchar(255) not null,
    business_name           varchar(255) not null,
    primary key (customer_account_id)
);
create index if not exists idx_cust_account on customer_accounts (customer_id, account_name);

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