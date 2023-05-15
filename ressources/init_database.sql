CREATE TABLE IF NOT EXISTS Stations (
    id integer NOT NULL PRIMARY KEY,
    city TEXT NOT NULL,
    brand TEXT NOT NULL,
    stationAddress TEXT NOT NULL,
    zipCode TEXT NOT NULL,
    gazole TEXT NOT NULL,
    sp95 TEXT NOT NUll,
    e10 TEXT NOT NULL,
    sp98 TEXT NOT NULL,
    e85 TEXT NOT NULL,
    gplc TEXT NOT NULL
)