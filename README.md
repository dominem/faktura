# Faktura

"Faktura" is a program for Polish invoice generation. It has been written for personal usage only (very basic needs).

## Usage

```shell
lein run example/invoice.edn example/invoice.pdf
```

where [`example/invoice.edn`](example/invoice.edn) is a path to invoice input data in EDN format, and [`example/invoice.pdf`](example/invoice.pdf) is an output path where the invoice, in PDF format, will be saved.

You can check the `example` dir to see how the input and output files look like.

## License

Copyright © 2019 Dominik Magdaleński

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
