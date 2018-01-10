def get_next_pass(password):
    passw = list(password)
    carry = 1
    for i in range(len(passw) - 1, -1, -1):
        if carry == 0:
            break
        if passw[i] == 'z':
            passw[i] = 'a'
            carry = 1
        else:
            passw[i] = chr(ord(passw[i]) + 1)
            carry = 0
    return ''.join(passw)

def is_valid(password):
    return has_increasing(password) and not contain_forbidden(password) and contain_dup(password)

def has_increasing(password):
    for i in range(0, 24):
        if ''.join([chr(ord('a') + i), chr(ord('a') + i + 1), chr(ord('a') + i + 2)]) in password:
            return True
    return False

def contain_forbidden(password):
    forbidden = ['i', 'o', 'l']
    for letter in forbidden:
        if letter in password:
            return True
    return False

def contain_dup(password):
    one_dup = None
    for i, letter in enumerate(password):
        if i < len(password) - 1:
            if letter == password[i+1]:
                one_dup = letter
                break

    if one_dup is None:
        return False

    two_dup = None
    for i, letter in enumerate(password):
        if i < len(password) - 1:
            if letter == password[i+1] and letter != one_dup:
                two_dup = letter
                return True

    return False

def get_next_valid_pass(password):
    count = 0
    while True:
        next_pass = get_next_pass(password)
        if is_valid(next_pass):
            count += 1
            if count == 2:
                return next_pass
        password = next_pass

def main():
    entry = input()
    resp = get_next_valid_pass(entry)
    print(resp)

if __name__ == '__main__':
    main()
