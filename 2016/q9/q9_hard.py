def parse_parens(expression):
    return [int(x) for x in expression.split('x')]

def get_decompress_len(entry):
    resp = 0 
    level = 0
    i = 0
    while i < len(entry):
        letter = entry[i]
        if letter == '(':
            closing_parens_index = i + entry[i:].find(')')
            n, times = parse_parens(entry[i+1:closing_parens_index]) 
            resp += get_decompress_len(entry[closing_parens_index+1:closing_parens_index+1+n]) * times
            i = closing_parens_index + 1 + n
        else:
            resp += 1
            i += 1
    return resp

def main():
    entry = input()
    entry = ''.join(entry.split())
    resp = get_decompress_len(entry)
    print(resp)

if __name__ == '__main__':
    main()
