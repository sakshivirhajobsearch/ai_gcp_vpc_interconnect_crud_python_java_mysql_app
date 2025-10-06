import mysql.connector
from config import MYSQL_HOST, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE

def get_all_vpcs():
    """Return a list of VPC dictionaries, skipping rows with missing names."""
    conn = mysql.connector.connect(
        host=MYSQL_HOST,
        user=MYSQL_USER,
        password=MYSQL_PASSWORD,
        database=MYSQL_DATABASE
    )
    cursor = conn.cursor()
    cursor.execute("SELECT id, vpc_name, subnet_name, region, ip_range FROM vpc_details")
    results = cursor.fetchall()

    vpcs = []
    for row in results:
        vpc_id, vpc_name, subnets, region, ip_range = row
        if not vpc_name:  # Skip invalid rows
            continue
        vpcs.append({
            "id": vpc_id,
            "name": vpc_name,
            "subnets": subnets,
            "region": region,
            "ip_range": ip_range
        })

    cursor.close()
    conn.close()
    return vpcs

def list_vpcs():
    """Print all VPCs to console (optional)."""
    for vpc in get_all_vpcs():
        print(f"ID: {vpc['id']}, Name: {vpc['name']}, Subnets: {vpc['subnets']}, Region: {vpc['region']}, IP Range: {vpc['ip_range']}")

if __name__ == "__main__":
    list_vpcs()
