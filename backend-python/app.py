from gcp_vpc import get_all_vpcs
from gcp_interconnect import get_all_interconnects
from ai_module import analyze_vpc_subnets

def display_vpcs():
    vpcs = get_all_vpcs()
    if not vpcs:
        print("No VPCs found.")
        return

    print("=== VPC Details ===")
    for vpc in vpcs:
        print(f"ID: {vpc['id']}, Name: {vpc['name']}, Subnets: {vpc['subnets']}, "
              f"Region: {vpc['region']}, IP Range: {vpc['ip_range']}")

def display_interconnects():
    ics = get_all_interconnects()
    if not ics:
        print("No Interconnects found.")
        return

    print("\n=== Interconnect Details ===")
    for ic in ics:
        print(f"ID: {ic['id']}, Name: {ic['name']}, Location: {ic['location']}, "
              f"Bandwidth: {ic['bandwidth']}, State: {ic['state']}")

def display_ai_insights():
    insights = analyze_vpc_subnets()
    if insights:
        print("\n=== AI Insights ===")
        for insight in insights:
            print(insight)
    else:
        print("\n=== AI Insights ===")
        print("No VPCs with issues detected.")

def main():
    display_vpcs()
    display_interconnects()
    display_ai_insights()

if __name__ == "__main__":
    main()
