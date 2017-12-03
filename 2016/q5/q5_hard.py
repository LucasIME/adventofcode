import hashlib

def md5(text):
    text = text.encode('utf-8')
    hash = hashlib.md5()
    hash.update(text)
    return hash.hexdigest()

def start_with_5_zero(entry):
    for i in range(5):
        if entry[i] != '0':
            return False
    return True

def decode(entry):
    id = 0
    password = [None for i in range(8)]
    filled = 0
    while filled < 8:
        cur_entry = entry + str(id)
        hash = md5(cur_entry)
        if start_with_5_zero(hash):
            index = int(hash[5]) if hash[5].isnumeric() else float('inf')
            item = hash[6]
            if index < 8 and password[index] == None:
                password[index] = item
                print(entry, id, password)
                filled += 1
        id += 1

    return ''.join(password)

def main():
    entry = input()
    resp = decode(entry)
    print(resp)

if __name__ == '__main__':
    main()
