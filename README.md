# Pinance Trading Platform

Welcome to the Pinance application! This application allows users to manage their financial activities, including account balances, cryptocurrency trading, and more. To get started with the application, please follow the instructions below.

## Database Setup

1. Create a new schema called `pinance_database` in your MySQL database.
2. Run the `Stored Procedure.sql` script provided in the `pinance_database` schema. This script will create the necessary stored procedures for the application.

## Layout Information

The Pinance application is designed to have a responsive layout, and it includes a specific layout optimized for iPhone 12 Pro. The iPhone 12 Pro layout has dimensions of 390x844 pixels.

## Getting Started

1. Clone this repository to your local machine.
2. Import the project into your favorite Java IDE (e.g., IntelliJ, Eclipse).
3. Make sure you have MySQL installed and running on your machine.
4. Create the `pinance_database` schema and run the `Stored Procedure.sql` script.
5. Update the database configuration in `application.properties` to match your MySQL credentials.
6. Run the application and access it through your preferred web browser.

## Application Features

- **Login:** Use your registered username and password to log in to the application.
- **Register:** Create a new account by providing your username, password, name, email.
- **Account Balance:** View your account balances for different cryptocurrencies and USD.
- **Market:** Access the cryptocurrency market to buy and sell various cryptocurrencies.
- **Deposit and Withdraw:** Deposit or withdraw funds from your account balance.

## Transaction History

All transaction history can be checked in the `transaction_history.log` file located in the `src/main/resources/log/` directory.

## iPhone 12 Pro Layout

For the best user experience on an iPhone 12 Pro, make sure to use a screen resolution of 390x844 pixels or a similar aspect ratio.

## Support

If you encounter any issues or have any questions regarding the Pinance application, please feel free to contact our support team at duy.vu@fdmgroup.com.

We hope you enjoy using Pinance and find it helpful in managing your finances effectively! Happy trading!
