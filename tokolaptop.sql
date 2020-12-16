-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 18, 2020 at 04:49 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tokolaptop`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id_barang` int(10) NOT NULL,
  `merk_barang` varchar(50) NOT NULL,
  `harga_barang` int(20) NOT NULL,
  `stok` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `merk_barang`, `harga_barang`, `stok`) VALUES
(1, 'ASUS', 5000000, 20),
(2, 'SAMSUNG', 6000000, 13),
(3, 'ACER', 3000000, 19),
(4, 'TOSHIBA', 7000000, 22),
(5, 'ASUS', 5000000, 17);

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(100) NOT NULL,
  `nama_karyawan` varchar(100) NOT NULL,
  `alamat_karyawan` varchar(200) NOT NULL,
  `no_hp_karyawan` varchar(100) NOT NULL,
  `jabatan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `alamat_karyawan`, `no_hp_karyawan`, `jabatan`) VALUES
('1', 'BOY', 'JAKARTA', '081234567890', 'ADMIN'),
('2', 'RANU', 'DEPOK', '081234567820', 'MANAGER'),
('3', 'IJUL', 'JAKARTA', '14045', 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('admin', 'admin'),
('bima', 'bima'),
('bunga', 'bunga');

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jenis_kelamin` varchar(20) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `id_transaksi` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama`, `jenis_kelamin`, `no_hp`, `alamat`, `id_transaksi`) VALUES
(1, 'BIMA', 'Laki-laki', '02190719723', 'JAKARTA\n', 'TR002'),
(2, 'BUNGA', 'Perempuan', '0218385378', 'JAKARTA\n', 'TR001'),
(3, 'DIKA', 'Laki-laki', '12412415135', 'BOGOR\n', 'TR004'),
(4, 'JASMIN', 'Perempuan', '081342567854', 'BOGOR\n', 'TR004'),
(5, 'MAYA', 'Perempuan', '081342521', 'JAKARTA', 'TR001');

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `id_transaksi` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `id_barang` varchar(50) NOT NULL,
  `merek` varchar(50) NOT NULL,
  `harga` int(100) NOT NULL,
  `jumlah` int(100) NOT NULL,
  `subtotal` int(100) NOT NULL,
  `total` int(100) NOT NULL,
  `bayar` int(100) NOT NULL,
  `kembali` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`id_transaksi`, `tanggal`, `id_barang`, `merek`, `harga`, `jumlah`, `subtotal`, `total`, `bayar`, `kembali`) VALUES
('TR001', '2020-08-15', '3', 'ACER', 3000000, 1, 3000000, 3000000, 3000000, 0),
('TR003', '2020-08-15', '2', 'SAMSUNG', 6000000, 1, 6000000, 6000000, 6000000, 0),
('TR004', '2020-08-18', '5', 'ASUS', 5000000, 1, 5000000, 5000000, 5000000, 0),
('TR005', '2020-08-18', '3', 'ACER', 3000000, 1, 3000000, 3000000, 5000000, 2000000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
