def get_captcha(entry):
    entry_list = [int(digit) for digit in entry]
    if len(entry) <= 1:
        return 0
    last_digit = entry_list[-1]
    resp = 0
    for digit in entry_list:
        if digit == last_digit:
            resp += digit
        last_digit = digit
    return resp

def main():
    entry = input()
    resp = get_captcha(entry)
    print(resp)

if __name__ == '__main__':
    main()
