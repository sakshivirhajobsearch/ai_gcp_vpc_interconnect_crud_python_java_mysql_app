import os
import warnings

# --- MySQL Configuration ---
MYSQL_HOST = "localhost"
MYSQL_USER = "ai_user"
MYSQL_PASSWORD = "admin"
MYSQL_DATABASE = "ai_gcp_vpc_interconnect"

db_config = {
    "host": MYSQL_HOST,
    "user": MYSQL_USER,
    "password": MYSQL_PASSWORD,
    "database": MYSQL_DATABASE
}

# --- GCP Service Account ---
GCP_SERVICE_ACCOUNT_FILE = os.path.join(os.getcwd(), "gcp-key.json")

try:
    from google.oauth2 import service_account
    credentials = service_account.Credentials.from_service_account_file(
        GCP_SERVICE_ACCOUNT_FILE
    )
except Exception as e:
    credentials = None
    warnings.warn(f"Could not load GCP credentials: {e}")
