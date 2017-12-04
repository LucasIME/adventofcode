def parse_parens(expression):
    return [int(x) for x in expression.split('x')]

def decompress(entry):
    resp = []
    level = 0
    i = 0
    while i < len(entry):
        letter = entry[i]
        if letter == '(':
            closing_parens_index = i + entry[i:].find(')')
            n, times = parse_parens(entry[i+1:closing_parens_index]) 
            resp.append(entry[closing_parens_index+1:closing_parens_index+1+n]*times)
            i = closing_parens_index + 1 + n
        else:
            resp.append(letter)
            i += 1
    return ''.join(resp)

def main():
    entry = input()
    entry = ''.join(entry.split())
    decompressed_entry = decompress(entry)
    resp = len(decompressed_entry)
    print(resp)

if __name__ == '__main__':
    main()
