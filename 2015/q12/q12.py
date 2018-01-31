import json

def json_sum(data):
    data_type = type(data)
    if data_type == int:
        return data
    if data_type == list:
        return sum([json_sum(x) for x in data])
    if data_type != dict:
        return 0
    cur_sum = 0
    for key, val in data.items():
        cur_sum += json_sum(key)
        cur_sum += json_sum(val)
    return cur_sum

def get_json_sum(entry):
    data = json.loads(entry)
    return json_sum(data)

def main():
    entry = input()
    resp = get_json_sum(entry)
    print(resp)

if __name__ == '__main__':
    main()
