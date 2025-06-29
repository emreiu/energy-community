CREATE TABLE IF NOT EXISTS usage_data
(
    hour
    TIMESTAMP
    PRIMARY
    KEY,
    community_produced
    DOUBLE
    PRECISION,
    community_used
    DOUBLE
    PRECISION,
    grid_used
    DOUBLE
    PRECISION
);

CREATE TABLE IF NOT EXISTS percentage_data
(
    our
    TIMESTAMP
    PRIMARY
    KEY,
    community_depleted
    DOUBLE
    PRECISION,
    grid_portion
    DOUBLE
    PRECISION
);
