
# Fdx-Mock-Resource-Server

## Description
This Mock Resource Server is designed to provide secure and standardized access to financial data of banks and financial institutions (FIs) in compliance with the Financial Data Exchange (FDX) version 6.0 standard. 
This server acts as a central hub for accessing account information, transaction details, payment network information, and other related data for authorized users.

## Features

* **Standardized Endpoints:** The resource server implements a set of endpoints following the FDX 6.0 standard, ensuring compatibility and consistency across different financial systems.
* **Secure Access:** Access to sensitive financial data is secured through authentication and authorization mechanisms, providing controlled access to authorized users only.
* **Comprehensive Data:** Retrieve various types of financial data including account lists, account details, transaction details, payment network information, and contact details for account holders.
* **Scalability:** Built to handle large volumes of data requests, the resource server is scalable to accommodate the growing needs of banks and financial institutions.

## List of Endpoints
1. **Get All Account List**: `/accounts` - Returns all accounts of the consented user.
2. **Account Details**: `/accounts/{account_id}` - Returns detailed account information for a specific account ID.
3. **Transaction Details**: `/account/{account_id}/transactions` - Returns transaction details for a specific account ID.
4. **Payment Network Details**: `/account/{account_id}/payment-networks` - Returns payment network information for a specific account ID.
5. **Contact Details**: `/account/{account_id}/contact` - Returns account holder information for a specific account ID.

## Prerequisites
1. An IDE that supports Java 17 or above (e.g., Eclipse, IntelliJ, STS).
2. Java installed on your local system.
3. Postman installed on your local system.

## Installation Instructions
1. Download the project from GitHub.
2. Import the project as an existing Maven project in your IDE.
3. Run the application as a Java Application.
4. Once the project is running, download the Postman collection from the resource/postman folder.
5. Import the downloaded Postman collection into Postman.
6. You can now use the imported collection to send requests to the API endpoints and observe the responses.

## Usage
1. To run the API, you need an authorization token from the `Fdx-Mock-Auth-Server` project. Refer to the README file of the `Fdx-Mock-Auth-Server` project for details.
2. Update the authorization token in the environment variable `authorize_token`.
3. Once the valid token is updated in the environment variable, you can hit the API endpoints to get the responses.

## License
This is an open-source project and does not have any specific licensing.

## Contact Information
For any queries, please post a comment on GitHub. We will look into it and get back to you.
