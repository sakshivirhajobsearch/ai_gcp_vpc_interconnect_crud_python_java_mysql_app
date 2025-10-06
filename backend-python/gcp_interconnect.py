import mysql.connector
from config import MYSQL_HOST, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE

def get_all_interconnects():
    """Return a list of Interconnect dictionaries, skipping rows with missing names."""
    conn = mysql.connector.connect(
        host=MYSQL_HOST,
        user=MYSQL_USER,
        password=MYSQL_PASSWORD,
        database=MYSQL_DATABASE
    )
    cursor = conn.cursor()
    cursor.execute("SELECT id, interconnect_name, location, bandwidth, state FROM interconnect_details")
    results = cursor.fetchall()

    ics = []
    for row in results:
        ic_id, name, location, bandwidth, state = row
        if not name:  # Skip invalid rows
            continue
        ics.append({
            "id": ic_id,
            "name": name,
            "location": location,
            "bandwidth": bandwidth,
            "state": state
        })

    cursor.close()
    conn.close()
    return ics

def list_interconnects():
    """Print all Interconnects to console (optional)."""
    for ic in get_all_interconnects():
        print(f"ID: {ic['id']}, Name: {ic['name']}, Location: {ic['location']}, Bandwidth: {ic['bandwidth']}, State: {ic['state']}")

if __name__ == "__main__":
    list_interconnects()
