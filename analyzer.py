from collections import defaultdict
from functools import reduce
from typing import List, Dict, Set


def total_time_per_user(logs: List[Dict]) -> Dict[str, float]:
    user_time = defaultdict(float)

    for log in logs:
        user_time[log["user"]] += log["duration"]

    return dict(user_time)


def most_active_users(logs: List[Dict], k: int) -> List[str]:
    user_time = total_time_per_user(logs)

    return [
        user for user, _ in sorted(
            user_time.items(),
            key=lambda x: x[1],
            reverse=True
        )[:k]
    ]


def unique_actions(logs: List[Dict]) -> Set[str]:
    return {log["action"] for log in logs}


def total_activity_time(logs: List[Dict]) -> float:
    return reduce(lambda acc, log: acc + log["duration"], logs, 0)


if __name__ == "__main__":

    logs = [
        {"user": "CSB001", "action": "YouTube", "duration": 2.5},
        {"user": "CSB002", "action": "Instagram", "duration": 1.5},
        {"user": "CSB001", "action": "LeetCode", "duration": 3.0},
        {"user": "CSB003", "action": "YouTube", "duration": 2.0},
        {"user": "CSB002", "action": "WhatsApp", "duration": 1.0}
    ]

    print("Total time per user:")
    print(total_time_per_user(logs))

    print("\nMost active users:")
    print(most_active_users(logs, 2))

    print("\nUnique actions:")
    print(unique_actions(logs))

    print("\nTotal activity time:")
    print(total_activity_time(logs))