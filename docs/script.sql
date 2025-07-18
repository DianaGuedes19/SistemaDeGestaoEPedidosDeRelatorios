
CREATE TABLE dbo.Client (
    ClientId   INT          IDENTITY PRIMARY KEY,
    Name       NVARCHAR(100) NOT NULL,
    Email      NVARCHAR(150) NOT NULL UNIQUE
);



CREATE TABLE dbo.[Order] (
   OrderId     INT          IDENTITY PRIMARY KEY,
    ClientId    INT          NOT NULL
    CONSTRAINT FK_Order_Client FOREIGN KEY REFERENCES dbo.Client(ClientId),
    CreationDate DATE        NOT NULL,
    Status       NVARCHAR(50) NOT NULL,
    Value        DECIMAL(10,2) NOT NULL
    );



CREATE TABLE dbo.OrderStatusHistory (
    HistoryId   INT            IDENTITY PRIMARY KEY,
    OrderId     INT            NOT NULL CONSTRAINT FK_History_Order FOREIGN KEY REFERENCES dbo.[Order](OrderId),
    OldStatus   NVARCHAR(50)   NOT NULL,
    NewStatus   NVARCHAR(50)   NOT NULL,
    ChangedAt   DATETIME2      NOT NULL DEFAULT SYSDATETIME()
);



CREATE TABLE dbo.ErrorLog (
    ErrorId    INT         IDENTITY PRIMARY KEY,
    OrderId    INT     NULL CONSTRAINT FK_Error_Order FOREIGN KEY REFERENCES dbo.[Order](OrderId),
    Message    NVARCHAR(MAX) NOT NULL,
    LoggedAt   DATETIME2     NOT NULL DEFAULT SYSDATETIME()
);

