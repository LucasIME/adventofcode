def get_complementary(index, size):
    return (index + int(size/2))%size

def get_captcha(entry):
    entry_list = [int(digit) for digit in entry]
    if len(entry) <= 1:
        return 0
    n = len(entry_list)
    resp = 0
    for i, digit in enumerate(entry_list):
        if digit == entry_list[get_complementary(i, n)]:
            resp += digit
    return resp

def main():
    entry = input()
    resp = get_captcha(entry)
    print(resp)

if __name__ == '__main__':
    main()
