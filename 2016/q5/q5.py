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
    password = []
    while len(password) < 8:
        cur_entry = entry + str(id)
        hash = md5(cur_entry)
        if start_with_5_zero(hash):
            print(entry, id, password)
            password.append(hash[5])
        id += 1

    return ''.join(password)

def main():
    entry = input()
    resp = decode(entry)
    print(resp)

if __name__ == '__main__':
    main()
