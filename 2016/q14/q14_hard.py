from hashlib import md5

def my_hash(entry):
    base = entry
    for year in range(2017):
        base = md5(base.encode('utf-8')).hexdigest()
    return base

class Keygen():
    def __init__(self, salt):
        self.index = 0
        self.salt = salt
        self.pre_gen_v = self.generate_first(30000)

    def get_next_key(self):
        self.index += 1
        while not self.is_key(self.pre_gen_v[self.index]):
            self.index += 1
        return self.pre_gen_v[self.index]

    def generate_first(self, n):
        resp = [0 for i in range(n)]
        for i in range(n):
            resp[i] = my_hash(self.salt + str(i))
        return resp

    def is_key(self, hash):
        has_three, base_c = self.contains_n(hash, 3)
        if has_three:
            return self.has_next(base_c)

        return False

    def contains_n(self, hash, n):
        for i in range(len(hash)-n+1):
            base = hash[i]
            has_diff = False
            for i2 in range(i, i+n):
                if hash[i2] != base:
                    has_diff = True
                    break

            if not has_diff:
                return True, base
        return False, None

    def contains_five(self, hash, c):
        for i in range(len(hash)-4):
            all_equal = True
            for i2 in range(i, i+5):
                if hash[i2] != c:
                    all_equal = False

            if all_equal:
                return True
        return False

    def has_next(self, base_c):
        for i in range(self.index + 1, self.index + 1000 + 1):
            if self.contains_five(self.pre_gen_v[i], base_c):
                return True
        return False

def process(salt):
    keygen = Keygen(salt)
    for i in range(64):
        key = keygen.get_next_key()
        print(key, keygen.index)
    return keygen.index

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
