import mysql.connector
from config import db_config

try:
    conn = mysql.connector.connect(**db_config)
    print("[INFO] MySQL connection successful!")
    conn.close()
except mysql.connector.Error as err:
    print(f"[ERROR] MySQL connection failed: {err}")
