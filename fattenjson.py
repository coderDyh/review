import json
#扁平化的处理数据

def flatten_json(json_data,parent_key='', sep='.'):
    flatten = {}
    if isinstance(json_data, dict):
        for k, v in json_data.items():
            key_str = f"{parent_key}{sep}{k}" if parent_key else k
            flatten.update(flatten_json(v, key_str, sep))
    elif isinstance(json_data, list):
        for i ,item in enumerate(json_data):
            key_str = f"{parent_key}{sep}{i}"
            flatten.update(flatten_json(item, key_str, sep))
    else:
        flatten[parent_key] = json_data
    return flatten

if __name__ == '__main__':
    json_data = {
        "key1": ["key1","key2"],
        "key2": {
            "city": "New York",
            "state": "NY",
            "zipCode": 10001
        },
        "key3": "key1"
    }
    flattened_json = flatten_json(json_data)
    print(json.dumps(flattened_json))