def process(entry_list):
    return sum(entry_list)

def main():
    entry_list = []
    while True:
       line = input()
       if line == '':
           break
       entry_list.append(int(line))
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

