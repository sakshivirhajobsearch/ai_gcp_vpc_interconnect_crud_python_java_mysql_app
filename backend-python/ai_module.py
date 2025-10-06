import mysql.connector
from config import MYSQL_HOST, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE

def analyze_vpc_subnets():
    """
    Analyze VPCs in the database for subnet issues.
    Skips rows with missing VPC names or subnets.
    Returns a list of AI insights.
    """
    try:
        conn = mysql.connector.connect(
            host=MYSQL_HOST,
            user=MYSQL_USER,
            password=MYSQL_PASSWORD,
            database=MYSQL_DATABASE
        )
        cursor = conn.cursor()
        cursor.execute("SELECT id, vpc_name, subnet_name FROM vpc_details")
        results = cursor.fetchall()

        insights = []
        for row in results:
            vpc_id, vpc_name, subnets = row

            # Skip rows with missing names
            if not vpc_name or not subnets:
                continue

            subnet_count = len(subnets.split(","))
            if subnet_count > 2:
                insights.append(
                    f"[AI Insight] VPC '{vpc_name}' (ID: {vpc_id}) has multiple subnets: {subnets}"
                )

        return insights

    except mysql.connector.Error as err:
        return [f"[ERROR] MySQL Error: {err}"]

    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()


if __name__ == "__main__":
    insights = analyze_vpc_subnets()
    if insights:
        for insight in insights:
            print(insight)
    else:
        print("[AI Insight] No VPCs with multiple subnets found.")
